import { Component, OnInit } from '@angular/core';
import { ShortPublication } from 'src/app/data/short-publication.model';
import { PublicationService } from 'src/app/services/publication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  latestPublications: ShortPublication[];

  primaryPublications: ShortPublication[];
  secondaryPublications: ShortPublication[];

  constructor(private publicationService: PublicationService) { }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData() {
    this.publicationService.getLatest()
      .subscribe(publications => {
          this.latestPublications = publications

          if (this.latestPublications.length > 3) {
            this.primaryPublications = this.latestPublications.slice(0, 2)
            this.secondaryPublications = this.latestPublications.slice(2)
          } else {
            this.primaryPublications = this.latestPublications
          }
        }
      );
  }

}
