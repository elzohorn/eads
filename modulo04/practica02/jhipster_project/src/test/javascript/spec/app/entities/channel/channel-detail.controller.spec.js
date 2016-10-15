'use strict';

describe('Controller Tests', function() {

    describe('Channel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChannel, MockPerson, MockPodcast, MockSubscription;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChannel = jasmine.createSpy('MockChannel');
            MockPerson = jasmine.createSpy('MockPerson');
            MockPodcast = jasmine.createSpy('MockPodcast');
            MockSubscription = jasmine.createSpy('MockSubscription');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Channel': MockChannel,
                'Person': MockPerson,
                'Podcast': MockPodcast,
                'Subscription': MockSubscription
            };
            createController = function() {
                $injector.get('$controller')("ChannelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'modulo04Practica02JhipsterApp:channelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
