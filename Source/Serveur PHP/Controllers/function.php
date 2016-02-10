<?php
function convertirdate($datebd){
		if($datebd != null )
			{
				
			$date = date_create($datebd);
					
			$date_peremption = date_format($date, DATE_ATOM);
			}
		else
			{
			$date_peremption = 'null';
			}
			
				return $date_peremption;
	}
	$header = array ('Content-Type' => 'application/json; charset=UTF-8','charset' => 'utf-8');

?>