# Docker Instructions

```docker pull mythgoudel/aj_analyze_stock_data``` (make sure you have permission to pull)

```docker run -it -d <container_id> bin/bash```

```docker exec -it <container_id> bash```

```cd app```

From here, you can run the appropriate jar to get the appropriate data with

```java -jar <jar_file>```

Once the appropriate files have been created, run ```docker cp <container_id>:app .```

This will copy the directory with the produced file and paste it into your current directory 

## GUI Instruction 

Download source code

Make sure Java 11 or higher is installed in your machine

Double click start.jar to run program

## Usage

Application pulls annual data and quarterly data for balance sheet, income statement, cash flow, and key ratios. The application also pulls future data by certified analysts from Yahoo Finance. The excel spread sheets are used to compare stocks side by side and the txt files is used to get a holistic view of the ticker.

## Copyright
Copyright 2021 Aarjab Goudel. All rights reserved 