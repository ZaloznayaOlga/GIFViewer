package zaloznaya.olga.app.gifviewer.data.mappers

interface IMapper<T,E>{
    fun mapFrom(from: T): E
}