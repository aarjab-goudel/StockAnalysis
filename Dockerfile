# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:11-jre-slim

RUN mkdir app

RUN mkdir app/logs

RUN apt-get update && apt-get install libgtk-3-0 libglu1-mesa xvfb -y && apt-get install -y vim && apt-get install -y libfreetype6 && apt-get install xvfb && apt-get update

COPY Stocks.txt app/Stocks.txt

COPY library/javafx-sdk-11.0.2 javafx-sdk-11.0.2 

COPY library/javafx-sdk-11.0.2/lib lib

COPY start.jar start.jar

RUN chmod a+x start.jar

COPY AnnualAndFuture.jar app/AnnualAndFuture.jar

RUN chmod a+x app/AnnualAndFuture.jar

COPY CreateAndAnalyzeAll.jar app/CreateAndAnalyzeAll.jar

RUN chmod a+x app/CreateAndAnalyzeAll.jar

COPY Quarterly.jar app/Quarterly.jar

RUN chmod a+x app/Quarterly.jar

COPY StockSheet.jar app/StockSheet.jar

CMD chmod a+x app/StockSheet.jar






 
