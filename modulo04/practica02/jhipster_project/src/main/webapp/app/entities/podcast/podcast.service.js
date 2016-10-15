(function() {
    'use strict';
    angular
        .module('modulo04Practica02JhipsterApp')
        .factory('Podcast', Podcast);

    Podcast.$inject = ['$resource'];

    function Podcast ($resource) {
        var resourceUrl =  'api/podcasts/:id';

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
