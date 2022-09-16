# On the Privacy of the Count-Min Sketch: Exploiting Positive Concentration

Master Thesis - Master Cybersecurity - UC3M

1. Run Attack/IPv4Universe.java. 
   Iterate through the whole IPv4 Universe making queries to the CMS. IP addresses with a count higher than a threshold set, are saved in a file.

   Arguments to run this class:
	  - 16384 				                   (w, can be changed)
	  - 4 				                       (d, can be changed)
	  - "/whole/path/input-traces.txt"	 (file with input traces to insert on CMS)
	  - "/whole/path/CMS-table.txt" 	   (name for the file to save CMS table)
	  - "/whole/path/results_ips.txt" 	 (name for the file to save results)
		

2. Run Attack/CountStatistics.java. 
   Compare counts obtained in previous step with the true ones. Calculates positive concentration of IP addresses with the same number of count obtained by    the attacker. The result is saved on a file.

   Arguments to run this class:
	  -  "/whole/path/results_ips.txt" 		            (file obtained from previous execution)
	  -  "/whole/path/input-traces.txt" 		          (file with traces used to insert data into CMS)
	  -  "/whole/path/positive_concentration.txt"     (name for the file to save results)


Detailed description of the code is explained on each class.

