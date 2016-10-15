(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('SubscriptionDetailController', SubscriptionDetailController);

    SubscriptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subscription', 'Person', 'Channel'];

    function SubscriptionDetailController($scope, $rootScope, $stateParams, previousState, entity, Subscription, Person, Channel) {
        var vm = this;

        vm.subscription = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('modulo04Practica02JhipsterApp:subscriptionUpdate', function(event, result) {
            vm.subscription = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
