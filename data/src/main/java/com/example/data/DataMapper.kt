package com.example.data

interface DataMapper<DomainModel> {

    fun toDomain(): DomainModel
}

fun <DataModel : DataMapper<DomainModel>, DomainModel> List<DataModel>.toDomain(): List<DomainModel> {
    return map { it.toDomain() }
}