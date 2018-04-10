export class PreferenceDependency {
    constructor(
        public subjectId?: number,
        public teacherId?: number,
        public divisionId?: number,
        public placeId?: number,
        public periodId?: number,
        public lessonId?: number,
        public divisionOwnerId?: number,
        public notTimetableId?: number,
        public date?: number,
        public dayOfWeek?: number,
        public everyWeek?: number,
        public startWithWeek?: number,
        public startTimeString?: string,
        public endTimeString?: string) {
    }

}
