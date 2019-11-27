import { Component, OnInit } from '@angular/core';
// import * as CanvasJS from './canvasjs.min';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { TrackUserActivityService } from 'src/app/service/track-user-activity.service';
import * as moment from 'moment';
import { bindCallback } from 'rxjs';
import * as async from 'async';

@Component({
  selector: 'app-track-user-activity',
  templateUrl: './track-user-activity.component.html',
  styleUrls: ['./track-user-activity.component.css']
})
export class TrackUserActivityComponent implements OnInit {
  minDate = new Date(2019, 0, 1);
  maxDate = new Date();
  pickedDate: Date;
  documentAdded = Array(12).fill(0);
  resultArray = [];
  hour: any;
  // documentsViewed = [];
  public barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time Period',
          fontColor: 'black',
          fontSize: 16
        }
      }], yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Number of Documents',
          fontColor: 'black',
          fontSize: 16
        }
      }]
    },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };
  public barChartLabels: Label[] = ['12-2AM', '2-4AM', '4-6AM', '6-8AM',
    '8-10AM', '10-12PM', '12-2PM', '2-4PM', '4-6PM', '6-8PM', '8-10PM', '10-12AM'];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [{ data: [] }];

  public chartColors: Array<any> = [
    { // first color
      backgroundColor: 'rgba(0,0,255, 1)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }];


  constructor(private trackUserActivity: TrackUserActivityService) { }

  ngOnInit() {
    console.log('in ngOnInit: ', (moment.utc(this.maxDate).format('YYYY-MM-DD')));
    this.trackUserActivity.getDocumentsAdded(moment.utc(this.maxDate).format('YYYY-MM-DD')).subscribe((data) => {
      this.resultArray = data.resultList;
      // tslint:disable-next-line: no-shadowed-variable
      this.resultArray.forEach((data, i) => {
        this.hour = data.addedOn.split('T')[1].split(':')[0];
        // tslint:disable-next-line: radix
        if (0 <= parseInt(this.hour) && parseInt(this.hour) < 2) {
          this.documentAdded[0]++;
        }
        // tslint:disable-next-line: radix
        if (2 <= parseInt(this.hour) && parseInt(this.hour) < 4) {
          this.documentAdded[1]++;
        }
        // tslint:disable-next-line: radix
        if (4 <= parseInt(this.hour) && parseInt(this.hour) < 6) {
          this.documentAdded[2]++; this.barChartData = [
            { data: this.documentAdded, label: 'Documents Added' },
            // { data: this.documentsViewed, label: 'Documents Viewed' }
          ];
        }
        // tslint:disable-next-line: radix
        if (6 <= parseInt(this.hour) && parseInt(this.hour) < 8) {
          this.documentAdded[3]++;
        }
        // tslint:disable-next-line: radix
        if (8 <= parseInt(this.hour) && parseInt(this.hour) < 10) {
          this.documentAdded[4]++;
        }
        // tslint:disable-next-line: radix
        if (10 <= parseInt(this.hour) && parseInt(this.hour) < 12) {
          this.documentAdded[5]++;
        }
        // tslint:disable-next-line: radix
        if (12 <= parseInt(this.hour) && parseInt(this.hour) < 14) {
          this.documentAdded[6]++;
        }
        // tslint:disable-next-line: radix
        if (14 <= parseInt(this.hour) && parseInt(this.hour) < 16) {
          this.documentAdded[7]++;
        }
        // tslint:disable-next-line: radix
        if (16 <= parseInt(this.hour) && parseInt(this.hour) < 18) {
          this.documentAdded[8]++;
        }
        // tslint:disable-next-line: radix
        if (18 <= parseInt(this.hour) && parseInt(this.hour) < 20) {
          this.documentAdded[9]++;
        }
        // tslint:disable-next-line: radix
        if (20 <= parseInt(this.hour) && parseInt(this.hour) < 22) {
          this.documentAdded[10]++;
        }
        // tslint:disable-next-line: radix
        if (22 <= parseInt(this.hour) && parseInt(this.hour) <= 23) {
          this.documentAdded[11]++;
        }
      });
      this.barChartData = [
        { data: this.documentAdded, label: 'Documents Added' },
        // { data: this.documentsViewed, label: 'Documents Viewed' }
      ];
    });
    // this.trackUserActivity.getDocumentsViewed(this.maxDate).subscribe((data) => {
    //   this.documentsViewed = data;
    // });
    // this.updateChart();

    // this.documentsViewed = [50, 50, 50, 50, 50, 50, 50, 50];


  }


  // events
  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }



  async track(pickedDate) {
    this.documentAdded.fill(0);
    console.log(pickedDate);
    console.log(moment.utc(pickedDate).format('YYYY-MM-DD'));
    await this.trackUserActivity.getDocumentsAdded(moment.utc(pickedDate).format('YYYY-MM-DD')).subscribe((data) => {
      this.resultArray = data.resultList;
      // tslint:disable-next-line: no-shadowed-variable
      this.resultArray.forEach((data, i) => {
        this.hour = data.addedOn.split('T')[1].split(':')[0];
        // tslint:disable-next-line: radix
        if (0 <= parseInt(this.hour) && parseInt(this.hour) < 2) {
          this.documentAdded[0]++;
        }
        // tslint:disable-next-line: radix
        if (2 <= parseInt(this.hour) && parseInt(this.hour) < 4) {
          this.documentAdded[1]++;
        }
        // tslint:disable-next-line: radix
        if (4 <= parseInt(this.hour) && parseInt(this.hour) < 6) {
          this.documentAdded[2]++;
        }
        // tslint:disable-next-line: radix
        if (6 <= parseInt(this.hour) && parseInt(this.hour) < 8) {
          this.documentAdded[3]++;
        }
        // tslint:disable-next-line: radix
        if (8 <= parseInt(this.hour) && parseInt(this.hour) < 10) {
          this.documentAdded[4]++;
        }
        // tslint:disable-next-line: radix
        if (10 <= parseInt(this.hour) && parseInt(this.hour) < 12) {
          this.documentAdded[5]++;
        }
        // tslint:disable-next-line: radix
        if (12 <= parseInt(this.hour) && parseInt(this.hour) < 14) {
          this.documentAdded[6]++;
        }
        // tslint:disable-next-line: radix
        if (14 <= parseInt(this.hour) && parseInt(this.hour) < 16) {
          this.documentAdded[7]++;
        }
        // tslint:disable-next-line: radix
        if (16 <= parseInt(this.hour) && parseInt(this.hour) < 18) {
          this.documentAdded[8]++;
        }
        // tslint:disable-next-line: radix
        if (18 <= parseInt(this.hour) && parseInt(this.hour) < 20) {
          this.documentAdded[9]++;
        }
        // tslint:disable-next-line: radix
        if (20 <= parseInt(this.hour) && parseInt(this.hour) < 22) {
          this.documentAdded[10]++;
          console.log(this.documentAdded[10]);
          console.log(this.documentAdded);
        }
        // tslint:disable-next-line: radix
        if (22 <= parseInt(this.hour) && parseInt(this.hour) <= 23) {
          this.documentAdded[11]++;
        }

      });
      console.log('in track..', this.resultArray);
      this.barChartData = [
        { data: this.documentAdded, label: 'Documents Added' },
        // { data: this.documentsViewed, label: 'Documents Viewed' }
      ];
    });
    // this.trackUserActivity.getDocumentsAdded(pickedDate).subscribe((data) => {
    //   this.documentAdded = data;
    // });
    // this.trackUserActivity.getDocumentsViewed(pickedDate).subscribe((data) => {
    //   this.documentsViewed = data;
    // });

    // this.documentsViewed = [17, 18, 25, 30, 10, 10, 10, 25, ];

  }


}
