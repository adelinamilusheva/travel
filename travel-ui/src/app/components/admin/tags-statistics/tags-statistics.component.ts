import { Component, OnInit } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { AuthService } from 'src/app/services/auth.service';
import { StatisticsService } from 'src/app/services/statistics.service';

@Component({
  selector: 'app-tags-statistics',
  templateUrl: './tags-statistics.component.html',
  styleUrls: ['./tags-statistics.component.css']
})
export class TagsStatisticsComponent implements OnInit {

  salesData: ChartData<'line'>;
  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: 'Брой четения по таг',
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
    this.statisticsService.getStatisticsTags().subscribe(s => 
      {
        let datasetArray: any[] = [];

        s.tagsReadings.forEach(tag => datasetArray.push({ label: tag.label, data: tag.readingsCount, tension: 0.5 }))
  
        this.salesData = {
          labels: s.dates,
          datasets: datasetArray,
        }
      }

    );
  }

//   exportGraph(event: any) {
//   //   var anchor = event.target;
//   //   anchor.href = document.getElementsByTagName('canvas')[0].toDataURL();
//   //   anchor.dowload = "test.png";

//   //  var file = new Blob([anchor], {type: 'application/png'});
//   //   var fileURL = URL.createObjectURL(file);
//   //   window.open(fileURL);

//     // const chartEl = document.getElementsByTagName('canvas')[0].toDataURL().replace("image/png", "image/octet-stream");
//     // window.location.href = chartEl;

//     // document.getElementsByTagName('canvas')[0].toBlob((blob) => {
//     //   let URLObj = window.URL || window.webkitURL;
//     //   let a = document.createElement("a");  
//     //   a.href = URLObj.createObjectURL(blob);
//     //   a.download = "untitled.png";
//     //   document.body.appendChild(a);
//     //   a.click();
//     //   document.body.removeChild(a);
//  });
// }
}
