const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            accounts:[],
            loans:[],
            cards_credit:[],
            cards_debit:[],
            cardType:"",
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data       
             this.accounts=res.data.accounts
             this.loans=res.data.loans
             this.cards_credit=res.data.cards.filter((e=>e.cardType=='CREDIT')).filter((e=>e.active==true))
             this.cards_debit=res.data.cards.filter((e=>e.cardType=='DEBIT')).filter((e=>e.active==true))
             
            })
    },
    methods:{
        formatDate(date){
          var fecha= date.split("");
          fecha=fecha.splice(0,7).join("")
          return fecha
        },
        EliminarTarjeta(card){
            Swal.fire({
                title: '¿Desea dar de baja la tarjeta?',
                text: "Esta acción no se podrá revertir",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.put('/api/clients/current/cards',
            	    "Number="+card.cardNumber,
            	    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                  .then(res => Swal.fire(
                   'Se realizo la baja con éxito!',
                    'vaya a la sección "Crear tarjetas" para solicitar otra.',
                    'success',))
			      .then(response => location.reload())
                .catch(res => Swal.fire(res.response.data,"Intente nuevamente","error"))
                  
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
