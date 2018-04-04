export class PreferenceHierarchy {
    constructor(
        public preferredByTeacher = 0,
        public preferredBySubject = 0,
        public preferredByPlace = 0,
        public preferredByDivision = 0,
        public tooSmallPlace = 0,
        public taken = 0,
        public points = 0) {
    }
}
