# Progetto "3A" Caruso&Fallica DSBD 2020

<p align="center" style="font-size: 24px">
  <span> Italiano </span> |
  <a href="https://github.com/Krytons/Progetto3ACarusoFallicaDSBD/blob/main/README.md">English</a>
</p>

## Progetto di Distributed Systems Big Data 2020
### A cura di:
- **Caruso Bartolomeo**
- **Giuseppe Fallica**

## 1. Obiettivo del progetto
Questo progetto mira a creare un microservizio che verrà utilizzato per gestire i pagamenti in una applicazione distribuita di e-commerce.

Gli strumenti da noi utilizzati per la realizzazione del progetto sono i seguenti:
- **Database MySql:** database relazionale utilizzato all’interno di un container Docker
- **Sistema di messaggistica Kafka:** piattaforma di streaming di eventi distribuita open source, utilizzata per pubblicare in un topic specifico, errori e informazioni sui dati critici.
- **Spring framework:** un framework open source utilizzato per sviluppare applicazioni basate su Java.
- **Apache Maven:** uno strumento di gestione e comprensione dei progetti.

### Come testare il nostro progetto
- **Avviare Docker:**
  ```bash
    $ docker start
  ```
- **Creare un file .env dentro "Progetto3ACarusoFallicaDSBD" con i seguenti argomenti:**
  ```dotenv
    DB_DATABASE = #Your DB name
    DB_USER = #Your DB user
    DB_PASSWORD = #Your DB password
    DB_ROOT_PASSWORD = #Your DB root password
    DB_HOST = #Your DB host name
    DB_PORT = #Your DB port number
    PAYMENT_MANAGER_HOST = #Host name of payment microservice, we used "paymentmanager"
    PAYMENT_MANAGER_PORT = #Payment microservice port
    MY_PAYPAL_ACCOUNT = #Receiver business mail, we used "merchange@mydomain.tld"
    HEART_BEAT_PERIOD = #Period of the heartbeating routine expressed in ms
    HEART_BEAT_URL_PING = #URL that will be used by the heartbeating routine for the POST request.
  ```

- **Aprire un terminale all'interno del "Progetto3ACarusoFallicaDSBD" ed usare il seguente comando:**
  ```bash  
  $ docker-compose up -build
  ```

### Troubleshooting
Se il nostro microservizio di pagamento non riesce a connettersi al database MySQL, potrebbero esserci dei problemi con i volumi di Docker.
I seguenti comandi docker possono risolvere questo problema, ma ciò eliminerà definitivamente il contenuto nel volume, cancellando qualsiasi database precedente ivi contenuto.
```bash  
  $ docker-compose down -v
  $ docker-compose up -d
```

---

## 2. Classi POJO
Per creare in maniera semplice messaggi e file JSON abbiamo utilizzato le seguenti classi POJO:
- **Kafka Message & Kafka Value:**
  Nel nostro progetto usiamo Kafka per pubblicare diversi tipi di informazioni in topic specifici. 
  Tutti i messaggi hanno la stessa struttura di base composta da un "message key" e un "message body", pertanto abbiamo creato una classe di base per il corpo del messaggio chiamata: "KafkaValue" caratterizzata da un insieme di valori:
    * **KafkaErrorValue:** valore utilizzato per i messaggi pubblicati nel topic "logging", con una corretta chiave di errore ad hoc.
    * **KafkaHttpValue:** valore utilizzato per i messaggi pubblicati nel topic "logging", con chiave "http_errors".
    * **KafkaOrderValue:** valore utilizzato per i messaggi pubblicati nel topic "orders", con chiave "order_paid
".

  ![KafkaMessages](./diagrams/kafkamessages.svg)


- **Ipn & PaypalIpn:**
  nel nostro progetto abbiamo due diversi tipi di servizi per gestire un ipn:
    * **Ipn simulato:** questo servizio viene utilizzato per simulare la ricezione di un Ipn. Questo servizio utilizzerà la classe POJO Ipn, che contiene solo gli attributi più importanti di un Ipn.

      ![KafkaMessages](./diagrams/ipn.svg)
    * **Ipn reale:** questo servizio viene utilizzato per ricevere un Ipn reale, utilizzando il servizio sandbox di Paypal. Per questo abbiamo utilizzato la classe POJO PaypalIpn, che contiene tutti gli attributi che un vero Ipn potrebbe avere.


- **Messaggio di ritorno:**
  quando viene generato un errore http, HttpExceptionController lo acquisisce e genera un messaggio di ritorno utilizzando la seguente classe POJO.

  ![KafkaMessages](diagrams/http.svg)

    
---

## 3. Kafka & Heartbeat
Il nostro microservizio utilizza il sistema di messaggistica Kafka per pubblicare in due topic, diversi tipi di informazioni di sistema:
- **Logging topic:** questo topic viene utilizzato per registrare tutti i messaggi di errore generati dal nostro microservizio. Esistono diversi tipi di errori:
    * `Received_wrong_business_paypal_payment`: questo messaggio di errore viene generato dalla classe PaymentService quando l'ipn ricevuto da Paypal contiene una mail aziendale del destinatario errata
    * `Bad_ipn_error`: questo messaggio di errore viene generato dalla classe PaymentService quando la richiesta Paypal ricevuta non può essere verificata.
    * `Http_error`: questo messaggio di errore viene generato quando HttpExceptionController acquisisce un errore http.
- **Orders topic:** questo topic viene utilizzato per registrare tutti i messaggi con la chiave order_paid. Questo tipo di messaggi viene generato quando un ipn è stato verificato con successo e salvato nella tabella dei pagamenti del database.

Le informazioni pubblicate dal nostro microservizio sono pronte per essere utilizzate da altri componenti che utilizzano Kafka.

Nei nostri progetti abbiamo utilizzato l'interfaccia "Configuration" per implementare le seguenti classi:
- **KafkaProducerConfig:** classe utilizzata per creare i nostri topic e per esporre un "KafkaTemplate" utilizzato dal servizio di pagamento per pubblicare le informazioni del nostro microservizio.
- **Heartbeater:** classe che implementa la strategia di ping in modalità hear-beat.

La nostra classe heartbeater ripete periodicamente la funzione "heartbeat()", che controllerà la connessione al nostro DB utilizzando una semplice query di selezione, e successivamente farà una richiesta POST a `HEART_BEAT_URL_PING`
con il seguente body:
```JSON
{
  "service": "serviceName",
  "serviceStatus": "up|down",
  "dbStatus": "up|down"
}
```

I seguenti diagrammi UML mostrano le interfacce utilizzate per le nostre classi "Heartbeater" e "KafkaProducerConfig":

![Kafka](./diagrams/kafka.svg)

---

## 4. Payment controller & Payment service
Come richiesto, la nostra classe Payment Controller espone i seguenti endpoint:
- `POST payment/ipn`: questo endpoint HTTP viene utilizzato per simulare una notifica di pagamento proveniente dal sistema Paypal.

  La richiesta deve contenere un header denominato "X-User-ID" che contiene uno userId che viene utilizzato dal servizio Paypal per generare correttamente un'istanza di pagamento.

  In questo particolare scenario, la nostra classe Payment Service richiede un argomento "ipn simulato" per generare un'istanza di pagamento: la funzione di verifica restituirà sempre un valore "vero".

  Per controllare facilmente se il corpo della richiesta ha tutto ciò che serve per generare una voce di pagamento, abbiamo creato una classe POJO chiamata "Ipn": il seguente JSON mostra un corretto body di una richiesta secondo la classe Ipn:
  ``` JSON
  {
    "invoice":"asjldfbksdag224",
    "item_id":13,
    "mc_gross":124.12,
    "business":"merchange@mydomain.tld"
  } 
  ```
  
Se un'istanza di Payment viene creata correttamente, verranno restituite tutte le informazioni sul pagamento come mostrato di seguito:
  ``` JSON  
  {
    "id": 27,
    "userId": 13,
    "orderId": "asjldfbksdag224",
    "amountPayed": 124.12,
    "createdAt": "2021-01-19T21:54:01.021+00:00",
    "modifiedAt": "2021-01-19T21:54:01.021+00:00"
  }
  ```
- `POST payment/real_ipn`: abbiamo introdotto questo endpoint HTTP per utilizzare il vero sistema Paypal per ricevere una notifica di pagamento.

  Come l'endpoint precedente, la richiesta deve contenere un UserId: in questo particolare scenario la richiesta proviene da Paypal, quindi non possiamo ricevere questo valore con l'Header chiamato "X-User-ID".

  Per evitare questo problema, l'URL di pagamento deve essere creato con un valore opzionale `on0` usato per contenere l'ID dell'utente.

  La seguente struttura JSON può essere utilizzata per generare un URL di pagamento:
  ``` JSON
   params = {
    "business": "merchange@mydomain.tld",
    "cmd": "_xclick",
    "invoice": 4,
    "amount": 12.55,
    "item_name": "my_order_string_reference",
    "item_number": 3,
    "quantity": 1,
    "currency_code": "EUR",
    "notify_url": "http://8acfc9049ed2.ngrok.io/payment/real_ipn",
    "on0": "1"
  }
  ```
  Per generare correttamente un URL di pagamento paypal si consiglia l'uso dello script disponibile al seguente link: https://github.com/Krytons/Paypal-URL-generator

  In questo scenario, la nostra classe Payment Service riceve un "Paypal Ipn" da Paypal, e in questo caso la funzione di verifica effettuerà una richiesta POST a paypal per verificare l'IPn.

  Se l'Ipn ricevuto è valido, verrà creata una voce di pagamento e tutte le informazioni a riguardo verranno restituite come l'endpoint precedente.

- `GET payment//transactions/fromTimestamp/endTimestamp`: questo endpoint HTTP viene utilizzato per ottenere tutte le transazioni effettuate tra i valori fromTimestamp e endTimestamp (espressi in unixTime).

  La richiesta deve contenere un Header denominato "X-User-ID": se il valore è 0 verranno restituite tutte le transazioni comprese tra fromTimestamp ed endTimestamp, se questo valore è diverso da 0 le transazioni restituite appartengono all'utente dell'identificativo dato.

  I pagamenti che soddisfano i requisiti della richiesta verranno restituiti come mostrato di seguito:
  ``` JSON
  [
    {
        "id": 1,
        "userId": 0,
        "orderId": "3",
        "amountPayed": 20.0,
        "createdAt": "2021-01-07T23:24:57.182+00:00",
        "modifiedAt": "2021-01-07T23:24:57.182+00:00"
    }
  ] 
  ```

Il seguente diagramma UML mostra le interfacce utilizzate per le nostre classi "PaymentController" e "PaymentService":

![Controller](./diagrams/controller.svg)
---

## 5. Gestione degli errori

Quando una richiesta HTTP fallisce, il nostro microservizio deve pubblicare un messaggio nel topic logging. Per fare ciò, abbiamo introdotto la classe `HttpExceptionController` che usa l'annotazione `@ComponentAdvice`: questa annotazione è una specializzazione di `@Component`.

Grazie a @ComponentAdvice, la nostra classe `@ExceptionHandler` è in grado di dichiarare due metodi:
- ``` Java
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> generateHttpErrorMessage(HttpServletRequest request, ResponseStatusException exception);
  ```
  Questo metodo è in grado di gestire tutte le `ResponseStatusException` generate dal nostro Controller o dal nostro Service.

  Tali eccezioni possono essere di due tipologie:
  - `5xx`: questo è un errore del server, quindi il messaggio pubblicato nel topic logging conterrà lo stack trace dell'eccezione, come mostrato di seguito:
    ``` JSON
    {
      "key":"http_errors",
      "value":{
        "sourceIp":"172.19.0.1",
        "service":"Payment_Service",
        "request":"/payment/ipn POST",
        "error":"org.springframework.web.server.ResponseStatusException: 500 INTERNAL_SERVER_ERROR \"duplicate_order_id\"\n\tat carusofallica.lab.paymentmanager.controller.PaymentController.ipnFunction(PaymentController.java:62)...",
        "timestamp":1611181971186
      }
    }
    ```
  - `4xx`: questo è un errore del client, quindi il messaggio pubblicato nel topic logging conterrà il codice di stato dell'eccezione, come mostrato di seguito:
    ``` JSON
    {
      "key":"http_errors",
      "value": {
        "sourceIp":"172.19.0.1",
        "service":"Payment_Service",
        "request":"/payment/ipn POST",
        "error":"400",
        "timestamp":1611181787733
      }
    }
    ```

- ``` Java
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView throwHttpNotFound(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
  ```
  Questo metodo è in grado di gestire tutte le `NoHandlerFoundException`, che vengono generate quando qualcuno fa una richiesta ad un URL non gestito: in questo caso il messaggio pubblicato nel topic logging conterrà il codice di stato dell'eccezione, come mostrato di seguito:

    ``` JSON
    {
      "key":"http_errors",
      "value":{
        "sourceIp":"172.19.0.1",
        "service":"Payment_Service",
        "request":"/payment/ipnz POST",
        "error":"404",
        "timestamp":1611182288650
      }
    }
    ``` 

Il seguente diagramma UML mostra le interfacce utilizzate per la nostra classe "HttpExceptionController":

![HttpExceptionController](./diagrams/http.svg)
---

