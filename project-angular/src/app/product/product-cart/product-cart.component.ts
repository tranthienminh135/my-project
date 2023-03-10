import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../user/service/auth.service';
import {Customer} from '../../user/model/customer';
import {CustomerService} from '../../user/service/customer.service';
import {CartService} from '../../user/service/cart.service';
import {ProductOrder} from '../model/productOrder';
import {ToastrService} from 'ngx-toastr';
import {CommonService} from '../../user/service/common.service';
import {render} from 'creditcardpayments/creditCardPayments';
import {Router} from '@angular/router';

declare var $: any;

@Component({
  selector: 'app-product-cart',
  templateUrl: './product-cart.component.html',
  styleUrls: ['./product-cart.component.css']
})
export class ProductCartComponent implements OnInit {
  public role: string;
  public username: string = '';
  public loginStatus: any;
  customer: Customer;
  productOrders: ProductOrder[] = [];
  totalMoney: number = 0;

  constructor(private authService: AuthService,
              private customerService: CustomerService,
              private cartService: CartService,
              private toastrService: ToastrService,
              private commonService: CommonService,
              private router: Router) {
    this.authService.checkLogin().subscribe(value => {
      this.loginStatus = value;
      if (value) {
        this.authService.getRoles().subscribe(resp => {
          this.getCustomerByUsername(resp.username);
        }, error => {
        });
      } else {
      }
    }, error => {
    });
  }

  ngOnInit(): void {
  }

  getProductInCardByCustomer(customer: Customer) {
    this.cartService.getProductInCardByCustomer(customer).subscribe((pos: ProductOrder[]) => {
      if (pos != null) {
        this.productOrders = pos;
        this.caculateTotalMoney(pos);
      } else {
        this.productOrders = [];
      }
    });
  }

  private renderPayment() {
    const target = $('#paymentsBtn');
    target.remove('#payments');
    target.html('<div id="payments" *ngIf="totalMoney >= 0"></div>');
    if (this.totalMoney >= 0) {
      render(
        {
          id: '#payments',
          currency: 'USD',
          value: String(((this.totalMoney + 50000) / 23000).toFixed(2)),
          onApprove: (details) => {
            if (details.status == 'COMPLETED') {
              this.onPaymentSuccess();
            }
          }
        }
      );
    }
  }

  private caculateTotalMoney(pos: ProductOrder[]) {
    this.totalMoney = 0;
    for (let i = 0; i < pos.length; i++) {
      this.totalMoney += ((pos[i].product.price - (pos[i].product.price * (pos[i].product.category.discountPercent / 100))) - ((pos[i].product.price - (pos[i].product.price * (pos[i].product.category.discountPercent / 100))) * pos[i].product.discountPercent / 100)) * pos[i].quantity;
    }
    this.renderPayment();
  }

  onPaymentSuccess() {
    $('#exampleModalPayment').modal('hide');
    this.router.navigateByUrl('/loading').then(() => {
      this.cartService.goPayment(this.customer).subscribe(() => {
        setTimeout(() => {
          this.router.navigateByUrl("/product/list").then(() => {
            this.toastrService.success('Thanh to??n th??nh c??ng!');
          })
        }, 500);
      });
      this.sendMessage();
    });
  }

  getCustomerByUsername(username: string) {
    this.customerService.getCustomerByUsername(username).subscribe(value => {
      this.customer = value;
      this.getProductInCardByCustomer(value);
    });
  }

  minusQuantity(productOrder: ProductOrder) {
    this.cartService.minusQuantity(productOrder).subscribe(value => {
      this.productOrders = value;
      this.caculateTotalMoney(value);
      this.sendMessage();
    }, error => {
      if (error.error.message == 'minimum') {

      }
    });
  }

  closeModal(id: number) {
    $('#exampleModalCenter' + id).modal('hide');
  }

  plusQuantity(productOrder: ProductOrder) {
    this.cartService.plusQuantity(productOrder).subscribe(value => {
      this.productOrders = value;
      this.caculateTotalMoney(value);
      this.sendMessage();
    }, error => {
      if (error.error.message == 'maximum') {
        this.toastrService.warning('S??? l?????ng s???n ph???m ???? t???i ??a.');
      }
    });
  }

  maximumQuantity() {
    this.toastrService.warning('S??? l?????ng s???n ph???m ???? t???i ??a.');
  }

  sendMessage(): void {
    this.commonService.sendUpdate('Success!');
  }

  deleteProductInCart(po: ProductOrder) {
    this.cartService.deleteProductInCard(po).subscribe((value: ProductOrder[]) => {
      this.productOrders = value;
      this.caculateTotalMoney(value);
      this.toastrService.success('X??a th??nh c??ng s???n ph???m ' + po.product.name + ' kh???i gi??? h??ng.');
      $('#deleteMinusModal' + po.product.id).modal('hide');
      $('#exampleModalDeleteButton' + po.product.id).modal('hide');
      this.sendMessage();
    }, error => {
      if (error.error.message == 'notfound') {
        this.toastrService.warning('Kh??ng t??m th???y s???n ph???m!');
      }
    });
  }
}
