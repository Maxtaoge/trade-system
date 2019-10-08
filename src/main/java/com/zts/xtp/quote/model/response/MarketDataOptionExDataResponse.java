// Generated by delombok at Sun Sep 22 21:37:45 CST 2019
package com.zts.xtp.quote.model.response;

public class MarketDataOptionExDataResponse {
    /**
     * 波段性中断参考价(SH)
     */
    double auctionPrice;
    /**
     * 波段性中断集合竞价虚拟匹配量(SH)
     */
    long auctionQty;
    /**
     * 最近询价时间(SH)
     */
    long lastEnquiryTime;


    @SuppressWarnings("all")
    public static class MarketDataOptionExDataResponseBuilder {
        @SuppressWarnings("all")
        private double auctionPrice;
        @SuppressWarnings("all")
        private long auctionQty;
        @SuppressWarnings("all")
        private long lastEnquiryTime;

        @SuppressWarnings("all")
        MarketDataOptionExDataResponseBuilder() {
        }

        @SuppressWarnings("all")
        public MarketDataOptionExDataResponseBuilder auctionPrice(final double auctionPrice) {
            this.auctionPrice = auctionPrice;
            return this;
        }

        @SuppressWarnings("all")
        public MarketDataOptionExDataResponseBuilder auctionQty(final long auctionQty) {
            this.auctionQty = auctionQty;
            return this;
        }

        @SuppressWarnings("all")
        public MarketDataOptionExDataResponseBuilder lastEnquiryTime(final long lastEnquiryTime) {
            this.lastEnquiryTime = lastEnquiryTime;
            return this;
        }

        @SuppressWarnings("all")
        public MarketDataOptionExDataResponse build() {
            return new MarketDataOptionExDataResponse(auctionPrice, auctionQty, lastEnquiryTime);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "MarketDataOptionExDataResponse.MarketDataOptionExDataResponseBuilder(auctionPrice=" + this.auctionPrice + ", auctionQty=" + this.auctionQty + ", lastEnquiryTime=" + this.lastEnquiryTime + ")";
        }
    }

    @SuppressWarnings("all")
    public static MarketDataOptionExDataResponseBuilder builder() {
        return new MarketDataOptionExDataResponseBuilder();
    }

    /**
     * 波段性中断参考价(SH)
     */
    @SuppressWarnings("all")
    public double getAuctionPrice() {
        return this.auctionPrice;
    }

    /**
     * 波段性中断集合竞价虚拟匹配量(SH)
     */
    @SuppressWarnings("all")
    public long getAuctionQty() {
        return this.auctionQty;
    }

    /**
     * 最近询价时间(SH)
     */
    @SuppressWarnings("all")
    public long getLastEnquiryTime() {
        return this.lastEnquiryTime;
    }

    /**
     * 波段性中断参考价(SH)
     */
    @SuppressWarnings("all")
    public void setAuctionPrice(final double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    /**
     * 波段性中断集合竞价虚拟匹配量(SH)
     */
    @SuppressWarnings("all")
    public void setAuctionQty(final long auctionQty) {
        this.auctionQty = auctionQty;
    }

    /**
     * 最近询价时间(SH)
     */
    @SuppressWarnings("all")
    public void setLastEnquiryTime(final long lastEnquiryTime) {
        this.lastEnquiryTime = lastEnquiryTime;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MarketDataOptionExDataResponse)) return false;
        final MarketDataOptionExDataResponse other = (MarketDataOptionExDataResponse) o;
        if (!other.canEqual((Object) this)) return false;
        if (Double.compare(this.getAuctionPrice(), other.getAuctionPrice()) != 0) return false;
        if (this.getAuctionQty() != other.getAuctionQty()) return false;
        if (this.getLastEnquiryTime() != other.getLastEnquiryTime()) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof MarketDataOptionExDataResponse;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $auctionPrice = Double.doubleToLongBits(this.getAuctionPrice());
        result = result * PRIME + (int) ($auctionPrice >>> 32 ^ $auctionPrice);
        final long $auctionQty = this.getAuctionQty();
        result = result * PRIME + (int) ($auctionQty >>> 32 ^ $auctionQty);
        final long $lastEnquiryTime = this.getLastEnquiryTime();
        result = result * PRIME + (int) ($lastEnquiryTime >>> 32 ^ $lastEnquiryTime);
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "MarketDataOptionExDataResponse(auctionPrice=" + this.getAuctionPrice() + ", auctionQty=" + this.getAuctionQty() + ", lastEnquiryTime=" + this.getLastEnquiryTime() + ")";
    }

    @SuppressWarnings("all")
    public MarketDataOptionExDataResponse() {
    }

    @SuppressWarnings("all")
    public MarketDataOptionExDataResponse(final double auctionPrice, final long auctionQty, final long lastEnquiryTime) {
        this.auctionPrice = auctionPrice;
        this.auctionQty = auctionQty;
        this.lastEnquiryTime = lastEnquiryTime;
    }
}