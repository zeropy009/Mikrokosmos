(function ($) {
    Array.prototype.addItem = function (item) {
        !!item && item !== undefined && this.push(item);
    };
    Array.prototype.removeItem = function (item) {
        if (!!item && item !== undefined) {
            let idx = this.indexOf(item);
            if (idx > -1) {
                this.splice(idx, 1);
            }
        }
    };
    Array.prototype.unique = function () {
        var arr = [];
        for (var i = 0; i < this.length; i++) {
            if (!arr.includes(this[i])) {
                arr.push(this[i]);
            }
        }
        return arr;
    };
    String.prototype.toArray = function () {
        return this.split(',');
    };
    String.prototype.byMaxLength = function (maxLength) {
        return this.search(/\./g) < 0 ? this.substring(0, maxLength) : this.substring(0, maxLength + 1);
    };
    Date.prototype.addHours = function (h) {
        //setTime and getTime work on miliseconds
        this.setTime(this.getTime() + (h * 3600000));
        return this;
    };
    maxLengthCurrency = function (maxLength) {
        return maxLength + parseInt((maxLength - 1) / 3);
    };
    customValidateFields = async function (formWithConstraints) {
        var elementList = formWithConstraints.form.querySelectorAll("input, textarea, select");

        await elementList.forEach(item => {
            formWithConstraints.validateFields(item);
        });
        this.autoFocusInValidField();

    };
    isSessionExpired = function (result) {
        if (result.redirected) {
            var ulrLogin = result.url;
            var endPath = ulrLogin.substr(ulrLogin.length - 6, ulrLogin.length);
            return '/login' === endPath;
        }
        return false;
    };
    equalHeight = function (container, element) {
        $(container).each(function () {
            var c = [];
            for (var i = 0; i < $(this).find(element).length; i++) {
                var height = $(this).find(element).eq(i).outerHeight();
                c.push(height);
            }
            $(this).find(element).outerHeight(Math.max.apply(Math, c));
        });
    };
    String.prototype.validationEmail = function () {
        //return true if email is invalid
        return !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,50})+$/.test(this);
    };
    checkFloatValue = function (formattedValue, floatValue) {
         if (formattedValue === "" || floatValue >= 0 && floatValue <= 999999999999999.90 || !floatValue) 
             return true;
          else
             return false;
    }
    checkIntegerValue = function (formattedValue, floatValue) {
         if (formattedValue === "" || floatValue >= 0 && floatValue <= 2147483647) 
             return true;
         else
             return false;
    }
    
    autoFocusInValidField = function(){
        let requireds = document.querySelectorAll("div.has-danger");
        let firstElement = requireds.length >0 && requireds[0].querySelector("input,textarea");
        if(!!firstElement){
            firstElement.focus();
        }
    }
    /*
     * Note this function use to calculator addition/subtraction decimal number in javascript
     * Math._add(number a, number b, precision default 2 number after point)
     * Math._add(2.07, -1.04) => 1.03 (normal in javascript 2.07 - 1.04 = 1.0299999999999998 ) for substraction
     * Math._add(0.1, 0.2) => 0.3 (normal in javascript 0.1 + 0.2 = 0.30000000000000004 ) for addition
    */
    Math._add = function (a, b, precision) {
        const x = Math.pow(10, precision || 2);
        return (Math.round(a * x) + Math.round(b * x)) / x;
    }
})(jQuery);

