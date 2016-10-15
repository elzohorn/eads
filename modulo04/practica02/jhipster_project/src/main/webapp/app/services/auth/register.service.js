(function () {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
