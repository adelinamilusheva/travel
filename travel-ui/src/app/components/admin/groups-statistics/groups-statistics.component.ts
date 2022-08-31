import { Component, OnInit } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { AuthService } from 'src/app/services/auth.service';
import { StatisticsService } from 'src/app/services/statistics.service';

@Component({
  selector: 'app-groups-statistics',
  templateUrl: './groups-statistics.component.html',
  styleUrls: ['./groups-statistics.component.css']
})
export class GroupsStatisticsComponent implements OnInit {
  salesData: ChartData<'line'>;
  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: 'Брой четения по група',
      },
    },
  };

  constructor(
    private statisticsService: StatisticsService,
    public authService: AuthService
    ) { }

  ngOnInit(): void {
    this.loadStatisticsTags();
  }

  private loadStatisticsTags() {
    this.statisticsService.getStatisticsGroups().subscribe(s => 
      {
        let datasetArray: any[] = [];

        s.groupsReadings.forEach(group => datasetArray.push({ label: group.label, data: group.readingsCount, tension: 0.5 }))
  
        this.salesData = {
          labels: s.dates,
          datasets: datasetArray,
        }
      }

    );
  }
}
