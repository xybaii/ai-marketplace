import { Component, OnInit } from '@angular/core';
import { ThemeService } from './services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    
    (function(d, m){
      var kommunicateSettings = 
          {"appId":"20eea0bdfa6328ea46547c3bc83519187","popupWidget":false,"automaticChatOpenOnNavigation":true};
      var s = document.createElement("script"); s.type = "text/javascript"; s.async = true;
      s.src = "https://widget.kommunicate.io/v2/kommunicate.app";
      var h = document.getElementsByTagName("head")[0]; h.appendChild(s);
      (window as any).kommunicate = m; m._globals = kommunicateSettings;
  })(document, (window as any).kommunicate || {});

  this.themeService.initializeTheme();

  }
  
  title = 'front-end';
}
