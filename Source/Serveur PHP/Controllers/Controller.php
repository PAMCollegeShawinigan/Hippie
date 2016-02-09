<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Routing\Controller as BaseController;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Foundation\Auth\Access\AuthorizesRequests;

abstract class Controller extends BaseController
{
    use AuthorizesRequests, DispatchesJobs, ValidatesRequests;
	
	
	public function convertirdate($datebd){
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
}
