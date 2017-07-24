/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TimetableTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IntervalDetailComponent } from '../../../../../../main/webapp/app/entities/interval/interval-detail.component';
import { IntervalService } from '../../../../../../main/webapp/app/entities/interval/interval.service';
import { Interval } from '../../../../../../main/webapp/app/entities/interval/interval.model';

describe('Component Tests', () => {

    describe('Interval Management Detail Component', () => {
        let comp: IntervalDetailComponent;
        let fixture: ComponentFixture<IntervalDetailComponent>;
        let service: IntervalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TimetableTestModule],
                declarations: [IntervalDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IntervalService,
                    JhiEventManager
                ]
            }).overrideTemplate(IntervalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntervalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Interval(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.interval).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
