import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

export class AuthInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>,
        next: HttpHandler): Observable<HttpEvent<any>> {

        const jwtToken = sessionStorage.getItem("token");
        var token = JSON.parse(jwtToken);
        if (token != null && token.jwt.length > 0) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${token.jwt}`
                }
            });
        }
        return next.handle(req);
    }
}
