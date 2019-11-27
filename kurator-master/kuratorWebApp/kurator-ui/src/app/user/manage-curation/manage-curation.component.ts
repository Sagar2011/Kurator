import { Component, OnInit } from '@angular/core';
import { TrendingService } from 'src/app/service/trending.service';

@Component({
  selector: 'app-manage-curation',
  templateUrl: './manage-curation.component.html',
  styleUrls: ['./manage-curation.component.css']
})
export class ManageCurationComponent implements OnInit {
  addedList: any = [];

  constructor(private trendingService: TrendingService
  ) { }
  favStatus = false;
  ngOnInit() {
    // added
    // trending
    this.trendingService.getAdded();
    this.trendingService.getValueAdd().subscribe(data => {
      this.addedList = data.resultList;
    });
  }

}
