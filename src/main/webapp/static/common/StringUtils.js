/**
 * 格式化字符串，替换相对应的值
 * @param args 入参:替换的KEY：value
 * @returns
 */
String.prototype.format = function(args) {
    if (arguments.length>0) {
        var result = this;
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                var reg=new RegExp ("({"+key+"})","g");
                result = result.replace(reg, args[key]);
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if(arguments[i]==undefined)
                {
                    return "";
                }
                else
                {
                    var reg=new RegExp ("({["+i+"]})","g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
        return result;
    }
    else {
        return this;
    }
};
/**
 * 去掉字符串前后的空格
 * @param str 入参:要去掉空格的字符串
 * @returns
 */
String.prototype.trimAll = function(){
    return this.replace(/(^\s*)|(\s*$)/g, '');
};
/**
 * 去掉字符串前的空格
 * @param str 入参:要去掉空格的字符串
 * @returns
 */
String.prototype.trimLeft = function(){
    return this.replace(/^\s*/g,'');
};
/**
 * 去掉字符串后的空格
 * @param str 入参:要去掉空格的字符串
 * @returns
 */
String.prototype.trimRight = function(){
    return this.replace(/\s*$/,'');
}
/**
 * 判断字符串是否为空
 * @param str 传入的字符串
 * @returns
 */
String.prototype.isNotEmpty = function(){
    var str = this;
    if("string" == (typeof  str) && str != '' && str.length > 0){
        return true;
    }
    return false;
}