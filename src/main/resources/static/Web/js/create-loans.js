const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            nombrePrestamo:"",
            amountMax:"",
            interes:"",
            payments:[],
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             
            })
    },
    methods:{
        CrearPrestamo(){
            Swal.fire({
                title: '¿Desea crear el préstamo?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loans',
                    "nombrePrestamo="+this.nombrePrestamo+"&"+"amountMax="+this.amountMax+"&"
                    +"porcent="+this.interes+"&"+"payments="+this.payments,
                    
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(res => Swal.fire(
                   'Préstamo creado con exito!',
                    'puede utilizarlo.',
                    'success',))                  
			      
                  
                  .catch(res=> Swal.fire(res.response.data,"revise la informacion","error"))
                  
                  
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