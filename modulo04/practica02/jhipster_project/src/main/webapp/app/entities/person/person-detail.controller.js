(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Podcast', 'Subscription', 'Channel'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Podcast, Subscription, Channel) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('modulo04Practica02JhipsterApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
