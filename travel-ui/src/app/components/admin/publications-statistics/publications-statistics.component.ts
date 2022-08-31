import { Component, OnInit } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { AuthService } from 'src/app/services/auth.service';
import { StatisticsService } from 'src/app/services/statistics.service';

@Component({
  selector: 'app-publications-statistics',
  templateUrl: './publications-statistics.component.html',
  styleUrls: ['./publications-statistics.component.css']
})
export class PublicationsStatisticsComponent implements OnInit {
  salesData: ChartData<'line'>;
  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: 'Брой четения на публикация',
      },
    },
  };

  constructor(
    private statisticsService: StatisticsService,
    public authService: AuthService
    ) { }

  ngOnInit(): void {
    this.loadStatisticsPublications();
  }

  private loadStatisticsPublications() {
    this.statisticsService.getStatisticsPublications().subscribe(s => 
      {
        let datasetArray: any[] = [];

        s.publicationsReadings.forEach(publication => datasetArray.push({ label: publication.label, data: publication.readingsCount, tension: 0.5 }))
  
        this.salesData = {
          labels: s.dates,
          datasets: datasetArray,
        }
      }

    );
  }
}
