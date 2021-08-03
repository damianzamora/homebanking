const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            cards:[],
            pagos:[],
            tarjetaSeleccionada:"",
            cvv:"",
            amount:"",
            descriptionCard:"",
            
            

            
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.pagos=res.data.pagos
             this.cards=res.data.cards.filter((e=>e.cardType=='CREDIT' || e.cardType==='DEBIT')).filter((e=>e.active==true))
             
            })
    },
    methods:{
        seleccionada(card){
            cardSelect=this.cards.filter((e=>e.cardNumber==card))
            this.date=cardSelect[0].thruDate
            return cardSelect[0].cardHolder+" "+cardSelect[0].cardType+" "+cardSelect[0].cardColor
        },
        realizarPago(){
            console.log(this.tarjetaSeleccionada)
            Swal.fire({
                title: '¿Desea realizar el pago?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/pago',
                    "cardNumber="+this.tarjetaSeleccionada
                    +"&"+
                    "amount="+this.amount
                    +"&"+
                    "cvv="+this.cvv
                    +"&"+
                    "description="+this.descriptionCard,
                    
                    
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(res => Swal.fire(
                   'Pago realizado con exito!',
                   'Muchas gracias',
                    "success",))
                  
			       .then(res => window.location.href = "pagos.html")
                  .catch(res=> Swal.fire(res.response.data,"Revise la informacion","error"))
                  
                  
                }
              })   

        },
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 