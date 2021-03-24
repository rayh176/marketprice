# marketprice

## JDK
Expected to use JDK 12

## How it works
	1. Data received by PricefeedListener
	2. String data is sent to CsvTransformer to transform to price feed [ Market Data]
    3. Every PriceFeed is sent to MarginMutator to update the Bid & Ask values [preceision of 5 is used for calculation]
    4. Transformed PriceFeed is published 
    

## Note
	Every execution is triggered in an executor with a single thread, every execution is killed after completion
	For Testing data is return from publisher so we can validate the results in the test
	
	