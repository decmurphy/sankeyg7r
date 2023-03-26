Put .CSV files in the `src/main/resources/transactions` folder. CSV files must have the following  example format:

```
Date,Details,Debit,Credit,Balance
20/03/2023,SOME DEBIT,5.00,,
10/03/2023,SOME OTHER DEBIT,16.50,,
07/03/2023,INCOME,,1290.90,420.69
```

Values need to be entered in reverse chronological order, because this is how my bank does it so you have to deal with
that as a thing. It doesn't matter if not every line has a value for `Balance`. As long as the first entry 
(chronologically speaking - so the last entry in the file) has a balance, it will figure out the rest. Each entry needs 
to have either a value for `Credit` or for `Debit`.

Run with 
```
./gradlew bootRun
```

Debug with
```
./gradlew debug
```
assuming you have a Remote JVM Debug target set up in IntelliJ.

You'll need to define your own Categories in Categoriser.kt

Output should look like