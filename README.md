# Sankey Generator

Convert bank transactions into [SankeyMatic](https://www.sankeymatic.com/build/) compatible inputs.

Supports Bank of Ireland and Revolut transaction dumps.

## Bank of Ireland

Put .csv files in the `src/main/resources/transactions/boi` folder.

## Revolut

Put .csv files in the `src/main/resources/transactions/revolut` folder.

## Run

Populate `src/main/resources/application.yaml` with your categories.

Run with 
```
./gradlew bootRun
```