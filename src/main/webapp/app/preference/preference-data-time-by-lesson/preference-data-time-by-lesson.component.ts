import {Component, Input, OnInit} from '@angular/core';
import {Preference} from '../preferecne.model';
import {DayOfWeek} from '../../entities/timetable/timetable.model';

@Component({
    selector: 'jhi-preference-data-time-by-lesson',
    templateUrl: './preference-data-time-by-lesson.component.html',
})
export class PreferenceDataTimeByLessonComponent implements OnInit {

    preferenceDataTimeSelectOption = Preference.preferenceSelectTypes;

    @Input()
    preferenceDataTime = [];

    preferenceByLessonList = [];


    ngOnInit(): void {
        this.initPreferenceDataTimeForTeachers();
        this.entityLessonSelectSort();
    }


    private initPreferenceDataTimeForTeachers() {
        const lessons = this.preferenceDataTime
            .map((preferenceDataTimeForTeacher) => ({lessonId: preferenceDataTimeForTeacher.lessonId, lessonName: preferenceDataTimeForTeacher.lessonName}));
        const lessonsWithoutDuplicate = lessons.filter( (element, index) => lessons.indexOf(lessons.find((lesson) => lesson.lessonId === element.lessonId)) === index);
        lessonsWithoutDuplicate
            .forEach((preferredLesson) => this.preferenceByLessonList
                .push({lessonId: preferredLesson.lessonId, lessonName: preferredLesson.lessonName, preferenceDataTimeByLessons: []}));

        this.preferenceByLessonList.forEach((preferenceByLesson) => {
                for (const dayOfWeek of Object.keys(DayOfWeek)) {
                    const numberDayOfWeek = Number(dayOfWeek);
                    if (!isNaN(numberDayOfWeek)) {
                        preferenceByLesson.preferenceDataTimeByLessons[numberDayOfWeek] = this.preferenceDataTime
                            .find((preference) => preference.lessonId === preferenceByLesson.lessonId && preference.dayOfWeek === numberDayOfWeek);
                    }
                }
            }
        );
    }

    private entityLessonSelectSort() {
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
