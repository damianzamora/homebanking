const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            cardType:"",
            cardColor:"",
            arrayCredit:[],
            arrayDebit:[],
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.arrayCredit=res.data.cards.filter((e=>e.cardType=='CREDIT')).filter((e=>e.active==true))
             this.arrayDebit=res.data.cards.filter((e=>e.cardType=='DEBIT')).filter((e=>e.active==true))
             
            })
    },
    methods:{
    
        CrearTarjeta(){
            Swal.fire({
                title: '¿Desea crear la Tarjeta?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/cards',
                    "cardType="+this.cardType+"&"+"cardColor="+this.cardColor,
                    {headers:{'accept':'application/xml'}})
                  .then(res => Swal.fire(
                   'Tarjeta creada con éxito!',
                    'Será direccionado a la sección tarjetas',
                    'success',))
			      .then(res => window.location.href = "cards.html")
                  .catch(res=> Swal.fire(res.response.data,"Asegurese de haber completado los campos.","error"))
                  
                }
              })   

        },
        
        formatDate(date){
            return new Date(date).toLocaleDateString('en-GB');
        },
        
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 