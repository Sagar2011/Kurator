// import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';

// export interface UsersFlagged {
//   name: string;
//   joinDate: any;
//   flaggedDocuments: number;
// }
// const ELEMENT_DATA: UsersFlagged[] = [

//   { name: 'Abhishek', joinDate: '2019-01-16', flaggedDocuments: 2 },
//   { name: 'Ankit', joinDate: '2019-01-16', flaggedDocuments: 3 },
//   { name: 'Sagar', joinDate: '2019-01-16', flaggedDocuments: 4 },
//   { name: 'Manoj', joinDate: '2019-01-16', flaggedDocuments: 1 },
//   { name: 'Indra', joinDate: '2019-01-16', flaggedDocuments: 2 },
//   { name: 'Pankaj', joinDate: '2019-01-16', flaggedDocuments: 2 },
//   { name: 'Krishna', joinDate: '2019-01-16', flaggedDocuments: 1 },
// ];

// @Component({
//   selector: 'app-admin',
//   templateUrl: './admin.component.html',
//   styleUrls: ['./admin.component.css']
// })
// export class AdminComponent implements OnInit {
//   constructor() { }

//   // tslint:disable-next-line: member-ordering
//   displayedColumns: string[] = ['name', 'joinDate', 'flaggedDocuments', 'actions'];
//   // tslint:disable-next-line: member-ordering
//   dataSource = ELEMENT_DATA;
//   public chartType = 'doughnut';

//   public chartDatasets: Array<any> = [
//     { data: [300, 50, 100, 40, 120], label: 'My First dataset' }
//   ];

//   public chartLabels: Array<any> = ['Java', 'Spring', 'C++', 'Angular', 'Python'];

//   public chartColors: Array<any> = [
//     {
//       backgroundColor: ['#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'],
//       hoverBackgroundColor: ['#FF5A5E', '#5AD3D1', '#FFC870', '#A8B3C5', '#616774'],
//       borderWidth: 2,
//     }
//   ];

//   public chartOptions: any = {
//     responsive: true
//   };
//   dataSource1 = [

//     [73, 39, 26, 39, 94, 0],

//     [93, 58, 53, 38, 26, 68],

//     [99, 28, 22, 4, 66, 90],

//     [14, 26, 97, 69, 69, 3],

//     [7, 46, 47, 47, 88, 6],

//     [41, 55, 73, 23, 3, 79],

//     [56, 69, 21, 86, 3, 33],

//     [45, 7, 53, 81, 95, 79],

//     [60, 77, 74, 68, 88, 51],

//     [25, 25, 10, 12, 78, 14],

//     [25, 56, 55, 58, 12, 82],

//     [74, 33, 88, 23, 86, 59]

//   ];
//   dataSource2 = [

//     [60, 77, 74, 68, 88, 51],

//     [25, 25, 10, 12, 78, 14],

//     [25, 56, 55, 58, 12, 82],

//     [74, 33, 88, 23, 86, 59],

//     [73, 39, 26, 39, 94, 0],

//     [93, 58, 53, 38, 26, 68],

//     [99, 28, 22, 4, 66, 90],

//     [14, 26, 97, 69, 69, 3],

//     [7, 46, 47, 47, 88, 6],

//     [41, 55, 73, 23, 3, 79],

//     [56, 69, 21, 86, 3, 33],

//     [45, 7, 53, 81, 95, 79]
//   ];
//   xAxis = {

//     labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July',

//       'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],

//   };

//   yAxis = {

//     labels: ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat'],

//   };
//   value = false;
//   public chartClicked(e: any): void { }
//   public chartHovered(e: any): void { }
//   ngOnInit() {
//     this.value = true;
//   }
//   documents() {
//     this.value = true;
//   }
//   users() {
//     this.value = false;
//   }

// }
