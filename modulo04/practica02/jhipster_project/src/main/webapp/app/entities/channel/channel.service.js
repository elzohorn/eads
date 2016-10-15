(function() {
    'use strict';
    angular
        .module('modulo04Practica02JhipsterApp')
        .factory('Channel', Channel);

    Channel.$inject = ['$resource'];

    function Channel ($resource) {
        var resourceUrl =  'api/channels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
