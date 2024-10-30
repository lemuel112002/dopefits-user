package com.example.dopefits.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dopefits.R
import com.example.dopefits.model.Product

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size

    fun updateProductList(newProductList: List<Product>) {
        val diffCallback = ProductDiffCallback(productList, newProductList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)

        fun bind(product: Product) {
            productName.text = product.title
            productPrice.text = product.price.toString()
            if (product.picUrl.isNotEmpty()) {
                Glide.with(itemView.context).load(product.picUrl[0]).into(productImage)
            }
            itemView.setOnClickListener { onItemClick(product) }
        }
    }
}