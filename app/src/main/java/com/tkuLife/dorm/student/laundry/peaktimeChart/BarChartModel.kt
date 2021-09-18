package com.tkuLife.dorm.student.laundry.peaktimeChart

import kotlin.collections.ArrayList
import kotlin.random.Random

class BarChartModel {

    companion object{
        val history by lazy {
            ArrayList<List<Int>>().also { dataSet->
//                dataSet.add(arrayListOf(10,21,13,25,11,2,5,6,5))
//                dataSet.add(arrayListOf(25,13,6,12,6,11,10,15,2))
//                dataSet.add(arrayListOf(8,19,10,2,16,20,15,5,5))
//                dataSet.add(arrayListOf(12,6,18,20,9,8,17,2,8))
//                dataSet.add(arrayListOf(3,17,20,18,10,5,9,7,11))
//                dataSet.add(arrayListOf(2,8,18,20,16,6,14,8,8))
//                dataSet.add(arrayListOf(14,6,18,16,10,11,15,3,7))
                for (i in 0..9){
                    dataSet.add(genRandArr())
                }

            }

        }
        fun countRate(array: List<Int>): ArrayList<Float> {
            return ArrayList<Float>().also { ArrayList->
                array.onEach {
                    ArrayList.add((it.toFloat()/array.sum())*100)
                }
            }
        }
        private fun genRandArr():List<Int>{
            return List(9){Random.nextInt(30,100)}
        }
    }


}