export class Time {

    hour: number;
    minute: number;
    formattedTime: string;

    constructor(public stringForm) {
        this.hour = parseInt(stringForm.split(':')[0], 10);
        this.minute = parseInt(stringForm.split(':')[1], 10);

        const hourString = this.hour.toString().length <= 1 ? '0' + this.hour : this.hour.toString();
        const minuteString = this.minute.toString().length <= 1 ? '0' + this.minute : this.minute.toString();
        this.formattedTime = hourString + ':' + minuteString;
    }

    getMinutes() {
        return 60 * this.hour + this.minute;
    }

}
