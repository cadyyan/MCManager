function GUID() {
    var guid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
    
    return guid;
}

function RpcRequest(method, params) {
    var json = {
        jsonrpc: '2.0',
        method: method,
        params: params,
        id: GUID()
    };
    
    return JSON.stringify(json);
}

function RpcAjaxRequest(method, params, success, failure) {
    if (params == undefined) {
        params = {};
    }
    
    var successWrapper = function (data) {
        if ($('#connection-error-dialog').closest('.ui-dialog').css('display') != 'none') {
            $('#connection-error-dialog').dialog('close');
        }
        
        if (success != undefined) {
            success(data);
        }
    }
    
    if (failure == undefined) {
        failure = function () {
            if ($('#connection-error-dialog').closest('.ui-dialog').css('display') == 'none') {
                $('#connection-error-dialog').dialog('open');
            }
        };
    }

    $.ajax({
        url: '/rpc',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: RpcRequest(method, params),
        dataType: 'json',
        success: successWrapper,
        error: failure
    });
}

$(document).ready(function () {
    var sections = $('div.section');
    for (var i = 0; i != sections.length; i++) {
        var section = $(sections[i]);
        section.addClass('ui-widget-content ui-corner-all');
        
        var header = $('<div>').html(section.attr('title'))
                               .addClass('title ui-widget-header');
        
        section.prepend(header);
    }
});
