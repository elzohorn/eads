(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/account/change_password', {}, {});

        return service;
    }
})();
