/**
 * create by HUWENMING on 2016.7.1
 * */

/**
 *
 * @param str
 * @returns {string}
 */
function getFmtEncode(str){
    var xxx = encodeURI(str);
    var new_str=""  ;
    for(var i = 0 ; i<xxx.length ; i++){
        new_str += xxx[i]+"_" ;
    }
    return new_str.replace(/%/g,"|");
}

