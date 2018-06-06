package com.client;

public class Date_valedation {

	public Date_valedation() {
		// TODO Auto-generated constructor stub
	}

	public boolean isValid(String sdate, String edate)
	{
		if (isValid(sdate) && isValid(edate)) {
			int[] sdate_specs = date_specs(sdate);
			int[] edate_specs = date_specs(edate);
			if (sdate_specs[2] < edate_specs[2] || edate_specs[2] == -1) {
				return true;

			}
			if (sdate_specs[2] <= edate_specs[2] || edate_specs[2] == -1) {
				if (sdate_specs[1] < edate_specs[1] || edate_specs[1] == -1) {
					return true;
				}
				if (sdate_specs[1] <= edate_specs[1] || edate_specs[1] == -1) {
					if (sdate_specs[0] <= edate_specs[0] || edate_specs[0] == -1) {
						return true;
					}
				}
			}
		}
		return false;

	}
	public boolean isValid(String date)
	{
		if(date.length()== 4){
			//return valid_year(date);
		}
		else if(date.length()== 6){
			return  (valid_day(date.substring(4, 6),date.substring(2, 4)));
					//(valid_year(date.substring(0, 2)) && valid_month(date.substring(2))) ||);
		}
		else if(date.length()== 8){
			return (valid_year(date.substring(0,4)) && valid_month(date.substring(4, 6)) && valid_day(date.substring(6, 8),date.substring(4, 6)));
		}
		else if(date.length()== 0){
			return true;
		}
		return false;}
	private boolean valid_day(String day, String month) {
		// TODO Auto-generated method stub
		if (Integer.parseInt(month) == 2) {
			if (Integer.parseInt(day) >0 &&  Integer.parseInt(day) <30) {
				return true;
			}
		}
		else if (Integer.parseInt(month) == 1 ||Integer.parseInt(month) == 3 ||Integer.parseInt(month) == 5 ||Integer.parseInt(month) == 7 ||Integer.parseInt(month) == 8 ||Integer.parseInt(month) == 10 ||Integer.parseInt(month) == 12  ) {
			if (Integer.parseInt(day) >0 &&  Integer.parseInt(day) <32) {
				return true;
			}
		}
		else {
			if (Integer.parseInt(day) >0 &&  Integer.parseInt(day) <31) {
				return true;
			}
		}
		return false;
	}

	public boolean valid_year(String year)
	{
		if (Integer.parseInt(year)>1920 && Integer.parseInt(year)<2025) {
			return true;
		}
		return false;

	}

	public boolean valid_month(String month)
	{
		if (Integer.parseInt(month)>0 && Integer.parseInt(month)<13) {
			return true;
		}
		return false;

	}

	public int[] date_specs(String date)
	{
		int [] result = {-1 ,-1, -1};
		if (date.length()==4) {
			result[2] = Integer.parseInt(date);
		}
		else if (date.length()==6) {
			result[2] = Integer.parseInt(date.substring(0, 2));
			result[1] = Integer.parseInt(date.substring(2,4));
			result[0] = Integer.parseInt(date.substring(4,6));
		}
		else if (date.length()==8) {
			result[2] = Integer.parseInt(date.substring(0, 4));
			result[1] = Integer.parseInt(date.substring(4, 6));
			result[0] = Integer.parseInt(date.substring(6));
		}
		return result;

	}

}
