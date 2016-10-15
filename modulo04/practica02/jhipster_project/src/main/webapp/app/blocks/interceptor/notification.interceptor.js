(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .factory('notificationInterceptor', notificationInterceptor);

    notificationInterceptor.$inject = ['$q', 'AlertService'];

    function notificationInterceptor ($q, AlertService) {
        var service = {
            response: response
        };

        return service;

        function response (response) {
            var alertKey = response.headers('X-modulo04Practica02JhipsterApp-alert');
            if (angular.isString(alertKey)) {
                AlertService.success(alertKey, { param : response.headers('X-modulo04Practica02JhipsterApp-params')});
            }
            return response;
        }
    }
})();
