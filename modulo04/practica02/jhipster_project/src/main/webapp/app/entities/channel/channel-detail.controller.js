(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('ChannelDetailController', ChannelDetailController);

    ChannelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Channel', 'Person', 'Podcast', 'Subscription'];

    function ChannelDetailController($scope, $rootScope, $stateParams, previousState, entity, Channel, Person, Podcast, Subscription) {
        var vm = this;

        vm.channel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('modulo04Practica02JhipsterApp:channelUpdate', function(event, result) {
            vm.channel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
