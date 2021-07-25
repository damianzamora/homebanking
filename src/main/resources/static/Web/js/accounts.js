const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            accounts:[],
            loans:[],
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data       
             this.accounts=res.data.accounts.filter(e=> e.active==true)
             this.loans=res.data.loans
            
            })
    },
    methods:{
        
        EliminarCuenta(cuenta){
            Swal.fire({
                title: 'Desea eliminar la Cuenta?',
                text: "Esta acción no se podrá revertir",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/Deleteaccounts',
            	    "number="+cuenta.number,
            	    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                  .then(res => Swal.fire(
                   'Cuenta eliminada con éxito!',
                    'vaya a la sección "Crear Cuentas" para solicitar otra.',
                    'success',))
			      .then(response => location.reload())
                .catch(res => Swal.fire(res.response.data,"Intente nuevamente","error"))
                  
                }
              })   


        },
        formatDate(date){
            return new Date(date).toLocaleDateString('en-GB');
        },
        calcularSaldo(cuenta){
            
            var aux=0;
            for(var i=0;i<cuenta.transactions.length;i++)
            {
                if(cuenta.transactions[i].type==="DEBITO")
                         aux=aux-cuenta.transactions[i].amount                
                else if(cuenta.transactions[i].type==="CREDITO")
                         aux=aux+cuenta.transactions[i].amount
            }
            return aux
        },
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 








