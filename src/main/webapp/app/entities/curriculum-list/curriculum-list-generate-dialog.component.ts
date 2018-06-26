import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';
import {Observable} from 'rxjs/Rx';
import {TranslateService} from '@ngx-translate/core';
import {Response} from '@angular/http';
import {GenerateReportModel} from './generate-report.model';
import {Chart} from 'chart.js'

Chart.pluginService.register( {
    beforeDraw: (chart) => {
        if (chart.config.options.elements.center) {
            // Get ctx from string
            const ctx = chart.chart.ctx;

            // Get options from the center object in options
            const centerConfig = chart.config.options.elements.center;
            const fontStyle = centerConfig.fontStyle || 'Arial';
            const txt = centerConfig.text;
            const color = centerConfig.color || '#000';
            const sidePadding = centerConfig.sidePadding || 20;
            const sidePaddingCalculated = (sidePadding / 100) * (chart.innerRadius * 2)
            // Start with a base font of 30px
            ctx.font = '30px ' + fontStyle;

            // Get the width of the string and also the width of the element minus 10 to give it 5px side padding
            const stringWidth = ctx.measureText(txt).width;
            const elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;

            // Find out how much the font can grow in width.
            const widthRatio = elementWidth / stringWidth;
            const newFontSize = Math.floor(30 * widthRatio);
            const elementHeight = (chart.innerRadius * 2);

            // Pick a new font size so it will not be larger than the height of label.
            const fontSizeToUse = Math.min(newFontSize, elementHeight);

            // Set font settings to draw it correctly.
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            const centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
            const centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);
            ctx.font = fontSizeToUse + 'px ' + fontStyle;
            ctx.fillStyle = color;

            // Draw text in center
            ctx.fillText(txt, centerX, centerY);
        }
    }
});

export enum GenerateState {
    BEFORE,
    DURING,
    SUCCESS,
    ERROR
}

@Component({
    selector: 'jhi-curriculum-list-generate-dialog',
    templateUrl: './curriculum-list-generate-dialog.component.html'
})
export class CurriculumListGenerateDialogComponent {

    timeCalculateChartLabels: string[] = [
        this.translateService.instant('timetableApp.curriculum-list.report.removeWindows'),
        this.translateService.instant('timetableApp.curriculum-list.report.calculatePreference'),
        this.translateService.instant('timetableApp.curriculum-list.report.calculateLessonAndDayOfWeek'),
        this.translateService.instant('timetableApp.curriculum-list.report.calculatePlace'),
        this.translateService.instant('timetableApp.curriculum-list.report.other'),
    ];
    timeCalculateChartData: number[];
    timeCalculateChartType = 'doughnut';

    timeCalculateOptions = {
        elements: {
            center: {
                text: '',
                color: '#827342',
                fontStyle: 'Helvetica',
                sidePadding: 15
            }
        }
    };

    timeRemoveWindowsChartLabels: string[] = [
        this.translateService.instant('timetableApp.curriculum-list.report.handicapInWindowsMethod'),
        this.translateService.instant('timetableApp.curriculum-list.report.swapInWindowMethod'),
        this.translateService.instant('timetableApp.curriculum-list.report.handicapNearToBlockMethod')
    ];
    timeRemoveWindowsChartData: number[];
    timeRemoveWindowsChartType = 'doughnut';

    timeRemoveWindowsOptions = {
        elements: {
            center: {
                text: '',
                color: '#827342',
                fontStyle: 'Helvetica',
                sidePadding: 15
            }
        }
    };

    barStatisticPointsChartOptions: any = {
        legend: {
            display: false
        },
        scaleShowVerticalLines: false,
        responsive: true
    };
    public barStatisticPointsChartLabels: string[] = [
        this.translateService.instant('timetableApp.curriculum-list.report.minPoints'),
        this.translateService.instant('timetableApp.curriculum-list.report.maxPoints'),
        this.translateService.instant('timetableApp.curriculum-list.report.averagePoints'),
        this.translateService.instant('timetableApp.curriculum-list.report.medianPoints')
    ];
    public barStatisticPointsChartType = 'bar';
    public barStatisticPointsChartData: any[];

    barStatisticIterationsChartOptions: any = {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        scaleShowVerticalLines: false,
        responsive: true
    };
    public barStatisticIterationsChartLabels: string[] = [
        this.translateService.instant('timetableApp.curriculum-list.report.handicapInWindowsMethod'),
        this.translateService.instant('timetableApp.curriculum-list.report.swapInWindowMethod'),
        this.translateService.instant('timetableApp.curriculum-list.report.handicapNearToBlockMethod')
    ];
    public barStatisticIterationsChartType = 'bar';
    public barStatisticIterationsChartData: any[] = [];

    GenerateState = GenerateState;
    curriculumList: CurriculumList;
    generateState = GenerateState.BEFORE;
    generateReport: GenerateReportModel;

    constructor(
        private curriculumListService: CurriculumListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private translateService: TranslateService
    ) {
    }

    close() {
        this.activeModal.dismiss('cancel');
    }

    private subscribeToSaveResponse(result: Observable<any>) {
        result.subscribe((res: Response) =>
            this.onSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSuccess(result: Response) {
        this.generateReport = result.json();
        this.updateChars();
        this.generateState = GenerateState.SUCCESS;
        this.eventManager.broadcast({name: 'generate', content: 'OK'});
    }

    private updateChars() {
        this.timeCalculateChartData = [
            this.generateReport.removeWindowsTimeImMs,
            this.generateReport.calculatePreferenceTimeImMs,
            this.generateReport.calculateLessonAndTimeInMs,
            this.generateReport.calculatePlaceTimeInMs,
            this.generateReport.otherTimeImMs
        ];
        const allTimeCalculateImS = (this.generateReport.allTimeImMs / 1000.0).toFixed(1);
        this.timeCalculateOptions.elements.center.text = allTimeCalculateImS.toString() + ' s';

        this.timeRemoveWindowsChartData = [
            this.generateReport.generateTimeHandicapInWindowsAlgorithmImMs,
            this.generateReport.generateTimeSwapInWindowAlgorithmImMs,
            this.generateReport.generateTimeHandicapNearToBlockAlgorithmImMs];
        const allTimeRemoveWindowsImS = (this.generateReport.removeWindowsTimeImMs / 1000.0).toFixed(1);
        this.timeRemoveWindowsOptions.elements.center.text = allTimeRemoveWindowsImS.toString() + ' s';

        this.barStatisticPointsChartData = [
            {
                data: [
                    this.generateReport.minPoint,
                    this.generateReport.maxPoints,
                    this.generateReport.averagePoints.toFixed(2),
                    this.generateReport.medianPoints],
                label: ''
            }
        ];

        this.barStatisticIterationsChartData = [
            {
                data: [
                    this.generateReport.numberOfHandicapAlgorithmIterations,
                    this.generateReport.numberOfSwapAlgorithmIterations,
                    this.generateReport.numberOfHandicapNearToBlockAlgorithmIterations],
                label: ''
            }
        ];
    }

    private onSaveError(result: Response) {
        console.log("fail")
        this.generateState = GenerateState.ERROR;
    }

    confirmGenerate(id: number) {
        this.generateState = GenerateState.DURING;
        this.subscribeToSaveResponse(this.curriculumListService.generate(id));
    }
}

@Component({
    selector: 'jhi-curriculum-list-delete-popup',
    template: ''
})
export class CurriculumListGeneratePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumListPopupService: CurriculumListPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.curriculumListPopupService
                .open(CurriculumListGenerateDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
