import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';
import {Observable} from 'rxjs/Rx';
import {Curriculum} from '../curriculum/curriculum.model';
import {Response} from '@angular/http';

@Component({
    selector: 'jhi-curriculum-list-report-dialog',
    templateUrl: './curriculum-list-report-dialog.component.html'
})
export class CurriculumListReportDialogComponent implements OnInit {

    curriculumList: CurriculumList;

    constructor(
        private curriculumListService: CurriculumListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        console.log("oninit")
    }

    private subscribeToSaveResponse(result: Observable<any>) {
        result.subscribe((res: Curriculum) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Curriculum) {
        this.eventManager.broadcast({ name: 'generate', content: 'OK'});
    }

    private onSaveError() {
    }

    generate(id: number) {
        this.subscribeToSaveResponse(this.curriculumListService.generate(id));
    }
}

@Component({
    selector: 'jhi-curriculum-list-report-popup',
    template: ''
})
export class CurriculumListReportPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumListPopupService: CurriculumListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.curriculumListPopupService
                .open(CurriculumListReportDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
