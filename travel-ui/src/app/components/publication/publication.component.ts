import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { firstValueFrom } from 'rxjs';
import { Publication } from 'src/app/data/publication.model';
import { Rating } from 'src/app/data/rating.model';
import { ReadComment } from 'src/app/data/read-comment.model';
import { ReadingStats } from 'src/app/data/reading-stats.model';
import { ShortPublication } from 'src/app/data/short-publication.model';
import { Weather } from 'src/app/data/weather.model';
import { CommentService } from 'src/app/services/comment.service';
import { PublicationService } from 'src/app/services/publication.service';
import { RatingService } from 'src/app/services/rating.service';
import { WeatherService } from 'src/app/services/weather.service';
import { AddEditModalComponent } from '../auxiliary/add-edit-modal/add-edit-modal.component';

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent implements OnInit {
  publication: Publication;
  similar: ShortPublication[];
  readingStats: ReadingStats;
  averageRating: number;
  ipAddress: string;
  isAlreadyRated: boolean;
  comments: ReadComment[];
  weather: Weather;

  badgeColors = ['bg-primary', 'bg-secondary', 'bg-success', 'bg-danger', 'bg-warning', 'bg-info'];

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private publicationService: PublicationService,
    private ratingService: RatingService,
    private commentService: CommentService,
    private weatherService: WeatherService,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
    this.badgeColors = this.badgeColors.sort(() => Math.random() - 0.5)
    this.averageRating = 0;
    this.comments = [];
    this.isAlreadyRated = true;
    this.route.params.subscribe(
      param => {
        const id = param['id']
        this.loadData(id)
      }
    );
  }

  private async loadData(id: number) {
    await this.getIPAddress(id);

    this.publicationService.getById(id).subscribe(response => {
      this.publication = response;

      if (this.publication.city) {
        let lang = localStorage.getItem('lang');
        this.weatherService.getByCityName(this.publication.city, lang).subscribe(response => this.weather = response);
      }
    });

    this.publicationService.getSimilar(id).subscribe(response => this.similar = response);

    this.publicationService.getReadings(id).subscribe(response => this.readingStats = response);

    this.ratingService.getAverageRating(id).subscribe(response => this.averageRating = response);

    this.commentService.getAllActiveByPublication(id).subscribe(response => this.comments = response);

    this.ratingService.isAlreadyRated(id, this.ipAddress).subscribe(response => {
      this.isAlreadyRated = response;
    })
  }

  rate(value: number) {
    let rating = new Rating(value, this.ipAddress, this.publication.id);

    this.ratingService.createRating(rating).subscribe(() => {
      window.location.reload();
    });
  }

  createOrUpdateComment(id?: number, content?: string) {
    const modalRef = this.modalService.open(AddEditModalComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.content = content;
    modalRef.componentInstance.ipAddress = this.ipAddress;
    modalRef.componentInstance.publicationId = this.publication.id;

    modalRef.result.then((data) => {
      window.location.reload();
    }, (reason) => {
      // on dismiss
    });
  }

  delete(id: number) {
    this.commentService.deleteComment(id).subscribe(() => {
      window.location.reload();
    });
  }

  private getIPAddress(id: number): Promise<any> {
    return firstValueFrom(this.http.get("http://api.ipify.org/?format=json")).then(response => {
      let str = "ip" as keyof typeof response;
      this.ipAddress = response[str].toString();
      this.publicationService.addReading(id, this.ipAddress).subscribe();
    });
  }  
}

