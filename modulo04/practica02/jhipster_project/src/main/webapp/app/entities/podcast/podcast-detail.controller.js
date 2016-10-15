(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('PodcastDetailController', PodcastDetailController);

    PodcastDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Podcast', 'Person', 'Channel'];

    function PodcastDetailController($scope, $rootScope, $stateParams, previousState, entity, Podcast, Person, Channel) {
        var vm = this;

        vm.podcast = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('modulo04Practica02JhipsterApp:podcastUpdate', function(event, result) {
            vm.podcast = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
