/* global moment */
/*
 *link document: https://momentjs.com/
 *             : https://momentjs.com/timezone/
*/
               
(function ($) {
    var defaultDateTimeFormat  = "YYYY-MM-DD HH:mm:ss";
    var defaultDateFormat  = "YYYY-MM-DD";
    var defaultTimeZone = "America/Curacao"; //GMT-4
    var formatMDY  = "MM/DD/YYYY";
    /*
     * Date.getNowString() -> "2019-02-21 04:39:07"
     * Date.getNowString("DD//MM/YYYY") -> "21/02/2019"
    */
    Date.getNowString = function (format){
        var formatDate = !!format ? format :defaultDateTimeFormat;
        return moment().tz(defaultTimeZone).format(formatDate);
    };
    /*
     * Date.getYMDString() -> "2019-02-21 04:39:07"
     * Date.getYMDString("DD//MM/YYYY") -> "21/02/2019"
    */
    Date.getYMDString = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return this.getNowString(formatDate);
    };
    /*
     * Date.getNow() -> Thu Feb 21 2019 05:07:21
    */
    Date.getNow = function (){
        return getMoment(this.getNowString())._d;
    };
    /*
     * Date.getYearGTMMinus4() -> 2019
    */
    Date.getYear= function (){
        return parseInt(this.getNowString("YYYY",defaultTimeZone));
    };
    /*
     * Date.getDateTimeTypeLong() -> 1550701244000
    */
    Date.getDateTimeTypeLong= function (){
        return getMoment(this.getNow()).tz(defaultTimeZone).valueOf();
    };
    /*
     * Date.getYMDTomorrow() -> "2019-02-22"
     * Date.getYMDTomorrow(DD-MM-YYYY HH:mm:ss) -> "22-02-2019 05:22:30"
    */
    Date.getYMDTomorrow = function (format){
        var formatDate = !!format ? format :defaultDateTimeFormat;
        return moment().tz(defaultTimeZone).add(1, 'days').format(formatDate);
    };
    /*
     * Date.getYMDYesterday() -> "2019-02-20"
     * Date.getYMDYesterday("DD-MM-YYYY HH:mm:ss") -> "20-02-2019 05:22:30"
    */
    Date.getYMDYesterday = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).subtract(1, 'days').format(formatDate);
    };
    /*
     * Date.getYMDOfLastMonth() -> "2019-01-21"
     * Date.getYMDOfLastMonth("DD-MM-YYYY HH:mm:ss") ->  "20-01-2019 05:22:30"
    */
    Date.getYMDOfLastMonth = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).subtract(1, 'month').format(formatDate);
    };
    /*
     * Date.getYMDOfLastWeek() -> "2019-02-14"
     * Date.getYMDOfLastWeek("DD-MM-YYYY HH:mm:ss") -> "14-02-2019 04:39:07"
    */
    Date.getYMDOfLastWeek = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).subtract(1, 'week').format(formatDate);
    };
    /*
     * Date.getYMDOfNextMonth() -> "2019-03-21"
     * Date.getYMDOfNextMonth("DD-MM-YYYY HH:mm:ss") -> "21-03-2019 04:39:07"
    */
    Date.getYMDOfNextMonth = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).add(1, 'month').format(formatDate);
    };
    /*
     * Date.getYMDOfNextWeek() -> "2019-02-28"
     * Date.getYMDOfNextWeek("DD-MM-YYYY HH:mm:ss") -> "28-02-2019 04:39:07"
    */
    Date.getYMDOfNextWeek = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).add(1, 'week').format(formatDate);
    };
    /*
     * Date.getYMDOfNextYear() -> "2020-02-28"
     * Date.getYMDOfNextYear("DD-MM-YYYY HH:mm:ss") -> "28-02-2020 04:39:07"
    */
    Date.getYMDOfNextYear = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).add(1, 'year').format(formatDate);
    };
    /*
     * Date.getYMDOfLastYear() -> "2018-02-28"
     * Date.getYMDOfLastYear("DD-MM-YYYY HH:mm:ss") -> "28-02-2018 04:39:07"
    */
    Date.getYMDOfLastYear = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return moment().tz(defaultTimeZone).subtract(1, 'year').format(formatDate);
    };
    /*
     * Date.stringToDate() -> Thu Feb 21 2019 05:07:21
     * Date.stringToDate("2019-02-21 04:30:00") -> Thu Feb 21 2019 04:30:00
    */
    Date.stringToDate = function (date){
        return !!date ? getMoment(date)._d:getMoment(this.getNowString())._d;
    };
    /*
     * let newDate = value type datetime or date from component
     * newDate.customFormatYMD() -> "2019-02-21"
     * newDate.customFormatYMD("DD/MM/YYYY HH:mm:ss") -> "21-02-2019 05:07:21"
    */
    Date.prototype.customFormatYMD = function (format){
        var formatDate = !!format ? format :defaultDateFormat;
        return getMoment(this).format(formatDate);
    };
    /*
     * Note need first parameter is datetime or date or null with format mm-dd-yyyy or yyyy-mm-dd
     * Date.customFormatYMD() -> "2019-02-21"
     * Date.customFormatYMD("DD/MM/YYYY HH:mm:ss") -> "Invalid date"
     * Date.customFormatYMD("2018-02-22") -> "2018-02-22"
     * Date.customFormatYMD("02-22-2018","YYYY-DD-MM") -> "2018-22-02"
    */
    Date.customFormatYMD = function (date,format){
        var formatDate = !!format ? format :defaultDateFormat;
        return !!date ? getMoment(date).format(formatDate):moment().format(formatDate);
    };
    /*
     * Note need first parameter is datetime or date or null with format mm-dd-yyyy or yyyy-mm-dd
     * Date.getDateOfPreviousMonth() -> "2019-07-16"
     * Date.getDateOfPreviousMonth("DD/MM/YYYY HH:mm:ss") -> "Invalid date"
     * Date.getDateOfPreviousMonth("2018-02-22") -> "2018-02-22"
     * Date.getDateOfPreviousMonth(1,"YYYY-DD-MM") -> "2019-06-16" (Now = "2019-07-16")
    */
    Date.getDateOfPreviousMonth = function (date,format){
        var formatDate = !!format ? format :defaultDateFormat;
        var date = parseInt(date)?date:0;
        return moment().tz(defaultTimeZone).subtract(date, 'month').format(formatDate);
    };
    /*
     * Note this function use to check string is invalid date format type MM/DD/YYYY
     * "2019/01/13".isDateTime() -> false
     * "2019/13/11".isDateTime() -> false
     * "13/12/2019".isDateTime() -> false
     * "02/28/2019".isDateTime() -> true
     * "02/29/2019".isDateTime() -> false
    */
    String.prototype.isDateTime = function (){
        return /^(((((0?[13578]\/|1[02]\/)(0?[1-9]|[12]\d|3[01])|(0?[13456789]\/|1[012]\/)(0?[1-9]|[12]\d|30)|0?2\/(0?[1-9]|[1-9]|1\d|2[0-8])))|((([02468][048]|[13579][26])00|\d{2}([13579][26]|0[48]|[2468][048])))\/0?2\/29)\/\d{4})$/.test(this) ;
    };
    /*
     * Note this function use to get date with time zone
    */
    getMoment = function(date){
        return !!date ? moment(date):moment();
    };
    /*
     * Note this function use to get true value of fromDate 
     * Date.validationFromDate("") -> false
     * Date.validationFromDate("2018-02-22") -> false
     * Date.validationFromDate("2018-02-22") -> true
     * Date.validationFromDate("2018-02-22") -> false
    */
    Date.validationFromDate = function(fromDate, toDate){
        return this.stringToDate(fromDate)<=this.stringToDate(toDate)?
            this.customFormatYMD(fromDate,formatMDY):this.customFormatYMD(toDate,formatMDY);
    }; 
    /*
     * Note this function use to get true value of toDate 
     * Date.validationToDate("07/15/2019","07/14/2019") => "07/15/2019" (NOW = "07/16/2019")
     * Date.validationToDate("","07/19/2019") -> "07/16/2019" (NOW = "07/16/2019")
     * Date.validationToDate("07/14/2019","07/15/2019") -> "07/15/2019" (NOW = "07/16/2019")
    */
    Date.validationToDate = function(fromDate, toDate){
        var nowDateString = this.getNowString(formatMDY);
        var valueToDate = this.stringToDate(toDate);
        return  fromDate.isDateTime() && valueToDate <= this.stringToDate(fromDate)?
                    this.customFormatYMD(fromDate,formatMDY)
                    :valueToDate >= this.getNow()?   
                        nowDateString:this.customFormatYMD(toDate,formatMDY); 
          
    }; 
    Date.getMinDate = function(dateOne, dateTwo){
        return  this.stringToDate(dateOne) <= this.stringToDate(dateTwo)?
                    this.customFormatYMD(dateOne,formatMDY):this.customFormatYMD(dateTwo,formatMDY); 
          
    };
    Date.getMaxDate = function(dateOne, dateTwo){
        return  this.stringToDate(dateOne) >= this.stringToDate(dateTwo)?
                    this.customFormatYMD(dateOne,formatMDY):this.customFormatYMD(dateTwo,formatMDY); 
          
    };
})(jQuery);

