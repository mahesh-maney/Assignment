Big Company's new FX feed
===========================
Big Company whose main currency is USD have signed a contract to receive a state of the art feed of exchange rates 
via a file which is delivered to them each day. Test Driven Development (TDD) approach is followed.

An example file is below:<br>
	START-OF-FILE<br>
		&nbsp;&nbsp;&nbsp;&nbsp; DATE=20181015<br>
		&nbsp;&nbsp;&nbsp;&nbsp;START-OF-FIELD-LIST<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CURRENCY<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EXCHANGE_RATE<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LAST_UPDATE<br>
		&nbsp;&nbsp;&nbsp;&nbsp;END-OF-FIELD-LIST<br>
		&nbsp;&nbsp;&nbsp;&nbsp;START-OF-EXCHANGE-RATES<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CHF| 0.9832 |17:12:59 10/14/2018| <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;GBP| 0.7849 |17:12:59 10/14/2018| <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EUR| 0.8677 |17:13:00 10/14/2018| <br>
		&nbsp;&nbsp;&nbsp;&nbsp;END-OF-EXCHANGE-RATES <br>
	END-OF-FILE <br>

The EXCHANGE_RATE is always against the US Dollar e.g. 1 USD = 0.7846 GBP.>br>
Each day a new file is delivered. Occasionally if a mistake has been made more than one file can be delivered intra-day.<br>

Big Company would like a program that continually processes these files and implements the following requirements:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;1. If a duplicate file is delivered it should be ignored<br>
	&nbsp;&nbsp;&nbsp;&nbsp;2. If a given rate varies by more than 20% day on day this should be "flagged"<br>
	&nbsp;&nbsp;&nbsp;&nbsp;3. A monthly average rate for each EXCHANGE_RATE should be calculated and published by the program<br>
	&nbsp;&nbsp;&nbsp;&nbsp;4. A yearly average rate for each EXCHANGE_RATE should be calculated and published by the program<br><br>
**Assumptions made :<br>**
	1. A program which writes its output to the "standard" output stream.
	2. There is no persistence, so when the program is terminated the state is lost.
	3. When there is a mistake made, more than one file will be delivered intra-day. Hence on a happy path day there is only file delivered.
	4. There will be no Graphical User Interface (other than using the command line) required and no persistence.

**Author :**
Mahesh Maney
