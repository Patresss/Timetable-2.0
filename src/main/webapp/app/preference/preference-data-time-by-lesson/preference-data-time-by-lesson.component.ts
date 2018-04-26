import {Component, Input, OnInit} from '@angular/core';
import {Preference} from '../preferecne.model';

@Component({
    selector: 'jhi-preference-data-time-by-lesson',
    templateUrl: './preference-data-time-by-lesson.component.html',
})
export class PreferenceDataTimeByLessonComponent implements OnInit {

    preferenceDataTimeSelectOption = Preference.preferenceSelectTypes;

    @Input()
    preferenceByLessonList = [];

    ngOnInit(): void {
        this.entityLessonSelectSort();
    }

    entityLessonSelectSort() {
        this.preferenceByLessonList.sort((a: any, b: any) => {
            if (!isNaN(Number(a.lessonName)) && isNaN(Number(b.lessonName))) {
                return -1;
            } else if (isNaN(Number(a.lessonName)) && !isNaN(Number(b.lessonName))) {
                return 1;
            } else if (!isNaN(Number(a.lessonName)) && !isNaN(Number(b.lessonName))) {
                return Number(a.lessonName) - Number(b.lessonName)
            } else {
                return a.lessonName.localeCompare(b.lessonName);
            }
        });
    }

}
